import {useEffect, useState} from "react";

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
        if (cart.filter(item => item.id === el.id && item.animalType === el.animalType) < 1) {
            setCart([...cart, el]);
        }
    };

    const removeFromCart = (el) => {
        let hardCopy = [...cart];
        hardCopy = hardCopy.filter((cartItem) => cartItem.id !== el.id && cartItem.animalType !== el.animalType);
        setCart(hardCopy);
    };

    return {
        cartTotal,
        cart,
        addToCart,
        removeFromCart
    }
}

export default useCart;
