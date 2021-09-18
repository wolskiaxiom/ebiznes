import React from "react";
import useCart from "../hooks/useCart";
import AnimalProductPanel from "./AnimalProductPanel";
import Cart from "./Cart";

const Shop = (animal) => {

    const {cartTotal, cart, addToCart, removeFromCart} = useCart();
    return (
        <div>
            {/*this cart to be quick summary of the cart*/}
            <Cart cartTotal={cartTotal} cart={cart} removeFromCart={removeFromCart} />
            <AnimalProductPanel animal={animal.animal} addItem={addToCart}/>
        </div>
    );
};

export default Shop;
