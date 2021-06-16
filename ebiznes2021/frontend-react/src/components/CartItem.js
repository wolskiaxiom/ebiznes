import React from 'react';
import RemoveButton from "./RemoveButton";

const CartItem = ({ item, removeItem }) => {
    const removeItemFromCart = () => removeItem(item)
    return (
        <li>
            <div key={item.id}>
                Nazywa sie {`${item.name} a kosztuje jedyne: ${item.price}`}
                <RemoveButton onClick={removeItemFromCart} label="Porzuć zwierzaka :( "/>
            </div>
        </li>
    )
}
export default CartItem;
