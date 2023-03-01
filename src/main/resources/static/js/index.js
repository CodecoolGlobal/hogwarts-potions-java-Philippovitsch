async function init() {
    const ingredients = await fetchData("http://localhost:8080/ingredients");
    addIngredients(ingredients);
}

async function fetchData(url) {
    const response = await fetch(url);
    return await response.json();
}

function addIngredients(ingredients) {
    const ingredientInput = document.querySelector("#ingredients");
    ingredients.forEach(ingredient => {
        const option = document.createElement("option");
        option.value = ingredient.name;
        option.innerText = ingredient.name;
        ingredientInput.append(option);
    });
}

init();
