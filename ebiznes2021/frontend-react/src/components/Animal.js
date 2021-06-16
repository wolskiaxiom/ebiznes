import React from 'react';
import CustomButton from './CustomButton';

const Animal = ({ item, addItem }) => {
    const addItemToBasket = () => addItem(item)
    return (
        <li className="store__item">
            <div>
                <b>{item.name}</b>
            </div>
            <div>Typ: {item.animalType}</div>
            <div>Wiek: {item.age}</div>
            <div>Cena: {item.price} PLN</div>
            <CustomButton onClick={addItemToBasket} label="Przygarnij zwierzaka" />
        </li>
    );
}

export default Animal;
