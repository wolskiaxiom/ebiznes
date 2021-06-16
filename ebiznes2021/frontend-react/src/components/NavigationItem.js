import React from 'react';
import CustomButton from "./CustomButton";

const NavigationItem = ({ animal, chooseAnimal}) => {

    return (
            <div>
                <CustomButton label={animal.categoryName} onClick={chooseAnimal}/>
            </div>
    )
}

export default NavigationItem