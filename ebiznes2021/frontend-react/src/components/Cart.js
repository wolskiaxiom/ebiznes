import React from 'react';
import CartItem from "./CartItem";

const Cart = ({cartTotal, cart, removeFromCart}) => {

    return (
        <div>
            <h2>Stan koszyka ze zwierzakami</h2>
            <ul>
                {cart.map((el) => (
                    <CartItem item={el} removeItem={removeFromCart}/>
                ))}
            </ul>
            <p>W zamian za: {cartTotal}</p>
        </div>
    )
}
export default Cart;