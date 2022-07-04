import React from 'react';
import CustomButton from './CustomButton';
import {CartState} from "../context/Context";
import {Card} from "react-bootstrap";

const Animal = ({ item, category }) => {
    const {
        state: { cart },
        dispatch,
    } = CartState()
    return (
        <div className={"products"}>
            <Card>
                <Card.Body>
                    <Card.Title>{item.name}</Card.Title>
                    <Card.Subtitle style={{paddingBottom: 10}}>
                        <div>Typ: {item.animalType}</div>
                        <div>Wiek: {item.age}</div>
                        <div>Cena: {item.price} PLN</div>
                    </Card.Subtitle>

                    {cart.filter(i => i.id === item.id && i.animalType === item.animalType)<1 ?
                        <CustomButton onClick={() => {
                            item.category = category;
                            dispatch({
                                type: "ADD_TO_CART",
                                payload: item
                            })
                        }
                        } label="Przygarnij zwierzaka" disabled={false}/>
                        :
                        <CustomButton label="Już w koszyku..." disabled={true}/>
                    }

                </Card.Body>
            </Card>
        </div>
    );
}

export default Animal;
