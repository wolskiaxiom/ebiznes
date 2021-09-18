import React from 'react';
import CustomButton from './CustomButton';
import {CartState} from "../context/Context";

const Animal = ({ item }) => {
    const {
        state: { cart },
        dispatch,
    } = CartState()

    return (
        <li className="store__item">
            <div>
                <b>{item.name}</b>
            </div>
            <div>Typ: {item.animalType}</div>
            <div>Wiek: {item.age}</div>
            <div>Cena: {item.price} PLN</div>
            {cart.filter(i => i.id === item.id && i.animalType === item.animalType)<1 ?
                <CustomButton onClick={() => {
                        dispatch({
                            type: "ADD_TO_CART",
                            payload: item
                        })
                    }
                } label="Przygarnij zwierzaka" disabled={false}/>
                :
                <CustomButton label="Już w koszyku..." disabled={true}/>
            }
        </li>
    );
}

export default Animal;
