import React from 'react';
import {Link} from "react-router-dom";

const NavigationItem = ({ animal, chooseAnimal}) => {

    return (
            <div>
                <Link class={"text-light"} style={{ textDecoration: 'none'}} to={animal.url}>{animal.categoryName}</Link>
            </div>
    )
}

export default NavigationItem