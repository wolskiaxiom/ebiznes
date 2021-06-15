import React, {useEffect, useState} from "react";

function useCart() {
    const [cart, setCart] = useState([]);
    const [cartTotal, setCartTotal] = useState(0);

    useEffect(() => {
        total();
    }, [cart]);

    const total = () => {
        let totalVal = 0;
        for (let i = 0; i < cart.length; i++) {
            totalVal += cart[i].price;
        }
        setCartTotal(totalVal);
    };

    const addToCart = (el) => {
        setCart([...cart, el]);
    };

    const removeFromCart = (el) => {
        let hardCopy = [...cart];
        hardCopy = hardCopy.filter((cartItem) => cartItem.id !== el.id);
        setCart(hardCopy);
    };

    const cartItems = cart.map((el) => (
        <div key={el.id}>
            {`${el.name}: $${el.price}`}
            <input type="submit" value="remove" onClick={() => removeFromCart(el)} />
        </div>
    ));

    return {
        cartTotal,
        cartItems,
        addToCart,
        removeFromCart
    }
}

export default useCart;
