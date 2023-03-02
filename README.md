# Hogwarts Potions

## Project description <em>(by Codecool)</em>

> What if I told you that magic is real? What if I told you that wizards, witches, and everything from the world of Harry
> Potter is real, and they need you now?
>
> In our fast-paced, modern world, they can't solve everything with spells and potions anymore. That's why now more than
> ever, Hogwarts School of Witchcraft and Wizardry needs a little bit of Muggle technology, to make the management of this
> enormous castle less of a headache. Of course, since witches and wizards have no idea about technology, you'll disguise
> your API as a set of more wizard-friendly spells, so that it's more intuitive for the target audience.
>
> It is time to spare parchments and digitize all the paperwork about persisting data by building a database.
> Also, the Student on Potions class could use some help, so let's see if we can lend a hand!
>
> Let the techno... ahem, magic, begin!

> ### What are you going to learn?
> - Build Web APIs in Spring
> - Build DAOs
> - Spring data
> - Spring data queries
> - Spring database connection
> - Spring JPA repositories

## Installation instructions

Recommended IDE: <b>IntelliJ IDEA</b>

- Clone or download repo
- Open project folder in IDE
- Wait until dependencies are synced
- Run Application

## Frontend description

- Tech stack: HTML, CSS, JavaScript, Java Spring, Thymeleaf
- URL: `http://localhost:8080/`
- Features:
  - Create new potions
  - Add ingredients to potions
  - Save new discovered potions to database
  - Delete potions
  - Search for potions with similar ingredients

## Backend description

- Tech stack: Java Spring, Lombok, H2 Database Engine
- URL: `http://localhost:8080/`
- API Endpoints:
  - Students:
    - `/student`, method: `GET`: Get all students
    - `/student`, method: `POST`: Add new student
    - `/student/{studentId}`, method: `GET`: Get student by student ID
    - `/student/{studentId}`, method: `PUT`: Update student by student ID
    - `/student/{studentId}`, method: `DELETE`: Delete student by student ID
  - Rooms:
    - `/room`, method: `GET`: Get all rooms
    - `/room`, method: `POST`: Add new room
    - `/room/{roomId}`, method: `GET`: Get room by room ID
    - `/room/{roomId}`, method: `PUT`: Update room by room ID
    - `/room/{roomId}`, method: `DELETE`: Delete room by room ID
    - `/room/rat-owners`, method: `GET`: Get rooms where no cat or owl lives
  - Potions:
    - `/potions`, method: `GET`: Get all potions
    - `/potions`, method: `POST`: Save new discovered potion as recipe
    - `/potions/{studentId}`, method: `GET`: Get all potions from a certain student by student ID
    - `/potions/brew/{studentId}`, method: `POST`: Create new potion by student ID
    - `/potions/{potionId}/add`, method: `PUT`: Add ingredient to potion by potion ID
    - `/potions/{potionId}/help`, method: `GET`: Get recipes with similar ingredients by potion ID
    - `/potions/{potionId}`, method: `DELETE`: Delete potion by potion ID
  - Ingredients:
    - `/ingredients`, method: `GET`: Get all ingredients
  - Recipes:
    - `/recipes`, method: `GET`: Get all recipes
