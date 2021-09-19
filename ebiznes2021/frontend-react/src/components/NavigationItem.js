import React from 'react';
import {Link} from "react-router-dom";

const NavigationItem = ({ animal, chooseAnimal}) => {

    return (
            <div style={{padding: "20px", display:"inline-block", background:"green"}}>
                <Link to={animal.url}>{animal.categoryName}</Link>
            </div>
    )
}

export default NavigationItem