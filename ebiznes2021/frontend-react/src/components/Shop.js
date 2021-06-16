import React from "react";
import useCart from "../hooks/useCart";
import AnimalProductPanel from "./AnimalProductPanel";
import Cart from "./Cart";
import NavigationItem from "./NavigationItem";

const animalCategories = [
    { url: '/cats', animalType: 'CAT', categoryName: 'Koty'},
    { url: '/dogs', animalType: 'DOG', categoryName: 'Psy'},
    { url: '/snakes', animalType: 'SNAKE', categoryName: 'Węże'},
    { url: '/parrots', animalType: 'PARROT', categoryName: 'Papugi'},
    { url: '/pigs', animalType: 'PIG', categoryName: 'Świnki morskie'},
    { url: '/hamsters', animalType: 'HAMSTER', categoryName: 'Chomiki'},
    { url: '/spiders', animalType: 'SPIDER', categoryName: 'Pająki'},
];

let chosenAnimal = animalCategories[0]

const Shop = () => {

    const {cartTotal, cart, addToCart, removeFromCart} = useCart();

    return (
        <div>
            {animalCategories.map(a => (
                <NavigationItem animal={a}/>
            ))}
            <Cart cartTotal={cartTotal} cart={cart} removeFromCart={removeFromCart} />
            <AnimalProductPanel animal={chosenAnimal} addItem={addToCart}/>
        </div>
    );
};

export default Shop;
