import React from "react";
import AnimalProductPanel from "./AnimalProductPanel";

const Shop = (animal) => {
    return (
        <div>
            <AnimalProductPanel animal={animal.animal}/>
        </div>
    );
};

export default Shop;
