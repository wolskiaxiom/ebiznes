import React from 'react';
import {Link} from "react-router-dom";
import CustomButton from "./CustomButton";

const NavigationItem = ({ animal, chooseAnimal}) => {

    return (
            <div>
                <Link to={animal.url}>{animal.categoryName}</Link>
            </div>
    )
}

export default NavigationItem