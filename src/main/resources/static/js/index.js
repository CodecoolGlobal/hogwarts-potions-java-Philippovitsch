let potionId = null;

async function init() {
    const addIngredientButton = document.querySelector("#add-ingredient");
    const helpButton = document.querySelector("#help");
    const deleteButton = document.querySelector("#delete");
    const studentIdInput = document.querySelector("#student-id")
    addIngredientButton.addEventListener("click", addIngredient);
    helpButton.addEventListener("click", displaySimilarRecipes);
    deleteButton.addEventListener("click", () => resetPage(true));
    studentIdInput.addEventListener("keypress", (event) => preventEnterInForm(event));
    displayMessage("Create a potion by entering a student id and adding the first ingredient.");
    const ingredients = await getData("http://localhost:8080/ingredients");
    displayAvailableIngredients(ingredients);
}

function preventEnterInForm(event) {
    if (event.key === "Enter") {
        event.preventDefault();
    }
}

async function getData(url) {
    const response = await fetch(url);
    const data = await response.text();
    return (data.length) ? JSON.parse(data) : null;
}

async function sendData(url, method, body) {
    const response = await fetch(url, {
        method: `${method}`,
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    });
    return await response.json();
}

function displayMessage(message) {
    const resultsContainer = document.querySelector("#results");
    const messageContainer = document.createElement("div");
    messageContainer.classList.add("message");
    messageContainer.textContent = message;
    resultsContainer.textContent = "";
    resultsContainer.append(messageContainer);
}

function displayAvailableIngredients(ingredients) {
    const ingredientInput = document.querySelector("#ingredients");
    ingredients.forEach(ingredient => {
        const option = document.createElement("option");
        option.value = ingredient.name;
        option.innerText = ingredient.name;
        ingredientInput.append(option);
    });
}

async function addIngredient() {
    const studentId = document.querySelector("#student-id").value;
    const ingredient = document.querySelector("#ingredients").value;

    if (studentId === "" || ingredient === "choose-ingredient") {
        displayMessage("Please enter a student id and choose an ingredient!");
        return;
    }
    if (!await isValidStudent(studentId)) {
        displayMessage("Student not found - please enter a valid student id!");
        return;
    }

    if (potionId === null) {
        await createNewPotion(studentId);
        toggleStudentIdInput();
    }

    const data = {"ingredientName": ingredient};
    const potion = await sendData(`http://localhost:8080/potions/${potionId}/add`, "PUT", data);

    displayPotion(potion);
    if (potion.brewingStatus === "DISCOVERY") {
        displaySaveMenu(potion);
    }
}

async function isValidStudent(studentId) {
    return await getData(`http://localhost:8080/student/${studentId}`) !== null;
}

function toggleStudentIdInput() {
    const inputField= document.querySelector("#student-id");
    inputField.disabled = potionId !== null;
}

async function createNewPotion(studentId) {
    const potion = await sendData(`http://localhost:8080/potions/brew/${studentId}`, "POST");
    potionId = potion.id;
}

function displayPotion(potion) {
    const resultsContainer = document.querySelector("#results");
    const potionContainer = document.createElement("div");
    potionContainer.classList.add("recipe");
    potionContainer.innerHTML = `
        <p><b>${potion.name}</b> (ID #${potion.id}):</p>
        <p><em>Brewer:</em> ${potion.brewer.name}</p>
        <p><em>Brewing status:</em> ${potion.brewingStatus}</p>
        <p><em>Ingredients:</em></p>
        <ul>${displayIngredients(potion.ingredients)}</ul>
    `;
    resultsContainer.textContent = "";
    resultsContainer.append(potionContainer);
}

function displayIngredients(ingredients) {
    let ingredientsList = "";
    ingredients.forEach(ingredient => {
        ingredientsList += `<li>${ingredient.name}</li>`;
    });
    return ingredientsList;
}

function displaySaveMenu(potion) {
    const resultsContainer = document.querySelector("#results");
    const saveRecipeContainer = document.createElement("div");
    saveRecipeContainer.classList.add("message");
    saveRecipeContainer.innerHTML = `
        <p>You discovered a new recipe! Do you want to save it?</p>
        <form>
            <label for="recipe-name">Recipe name: </label>
            <input type="text" id="recipe-name"><br>
            <input type="button" class="button" id="save-discovered-recipe" value="Save recipe">
            <input type="button" class="button" id="reset-discovered-recipe" value="Reset">
        </form>
    `;
    resultsContainer.append(saveRecipeContainer);

    const saveDiscoveredRecipeButton = document.querySelector("#save-discovered-recipe");
    const resetDiscoveredRecipeButton = document.querySelector("#reset-discovered-recipe");
    saveDiscoveredRecipeButton.addEventListener("click", () => saveRecipe(potion));
    resetDiscoveredRecipeButton.addEventListener("click", () => resetPage(false));
}

async function saveRecipe(potion) {
    const studentId = document.querySelector("#student-id").value;
    const potionName = document.querySelector("#recipe-name").value;

    const ingredients = [];
    potion.ingredients.forEach(ingredient => {
        ingredients.push(ingredient.name);
    });

    const data = {
        "potionName": potionName,
        "studentId": studentId,
        "ingredients": ingredients
    };
    await sendData("http://localhost:8080/potions", "POST", data);
    const messageContainer = document.querySelector(".message");
    messageContainer.textContent = "Recipe saved!";
}

async function resetPage(deletePotion) {
    const studentId = document.querySelector("#student-id");
    const ingredient = document.querySelector("#ingredients");

    if (deletePotion && potionId !== null) {
        await sendData(`http://localhost:8080/potions/${potionId}`, "DELETE");
    }

    potionId = null;
    studentId.value = "";
    ingredient.value = "choose-ingredient";
    toggleStudentIdInput();
    displayMessage("Create a potion by entering a student id and adding the first ingredient.");
}

async function displaySimilarRecipes() {
    if (potionId === null) {
        displayMessage(`
            You have to start brewing a potion first!
            Please enter a student id and choose an ingredient!
        `);
        return;
    }

    const similarRecipes = await getData(`http://localhost:8080/potions/${potionId}/help`);

    if (similarRecipes.length === 0){
        displayMessage("No similar recipes found!");
        return;
    }

    const resultsContainer = document.querySelector("#results");
    resultsContainer.innerHTML = "<p><b>Similar recipes:</b></p>";
    similarRecipes.forEach(recipe => {
        const recipeContainer = document.createElement("div");
        recipeContainer.classList.add("recipe");
        recipeContainer.innerHTML = `
            <p><em>Name:</em> ${recipe.name}</p>
            <p><em>Brewer:</em> ${recipe.brewer.name}</p>
            <p><em>Ingredients:</em></p>
            <ul>${displayIngredients(recipe.ingredients)}</ul>
        `;
        resultsContainer.append(recipeContainer);
    });
}

init();
