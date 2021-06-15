import React from "react";
import Item from '../components/Item'
import useCart from "../components/useCart";
import ProductsPanel from "../components/ProductsPanel";

const Shop = () => {
    const items = [
        {
            id: 1,
            name: "Dogi",
            animalType: "doggo",
            age: 2,
            price: 20,
        },
        {
            id: 2,
            name: "cati",
            animalType: "cat",
            age: 3,
            price: 32,
        },
        {
            id: 3,
            name: "papug",
            animalType: "papuga",
            age: 11,
            price: 51,
        },
    ];

    const {cartTotal, cartItems, addToCart, removeFromCart} = useCart();

    const listItems = items.map((el) => (
        <Item item={el} addItem={addToCart}/>
    ))

    return (
        <div>
            STORE
            <div>{listItems}</div>
            <div>CART</div>
            <div>{cartItems}</div>
            <div>Total: ${cartTotal}</div>
            <ProductsPanel product={{ url: '/cats', animalType: 'CATS', categoryName: 'Koty'}} addItem={addToCart}/>
        </div>
    );
};

export default Shop;
