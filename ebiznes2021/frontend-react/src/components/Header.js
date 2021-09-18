import React from "react";
import animalCategories from "../constants/animal-categories.json"
import NavigationItem from "./NavigationItem";

const Header = () => {
    return (
        <div>
            {animalCategories.map(a => (
                <NavigationItem animal={a} key={a.animalType}/>
            ))}
        </div>
    )
}

export default Header