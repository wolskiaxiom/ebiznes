import React from 'react';
import useFetch from '../hooks/useFetch';
import Item from './Item';


const ProductsPanel = ({ product, addItem }) => {
    const { response: animalList, loading, error } = useFetch(`http://localhost:9000${product.url}`, {});
    return (
        <div>
            <h1>{product.label}</h1>
            {loading && <p>Loading...</p>}
            {error && <p>Something went wrong...</p>}
            {animalList && animalList.length > 0 &&
                <ul className="store__items">
                    {animalList.map(animal => (
                        <Item item={animal} addItem={addItem}/>
                    ))}
                </ul>
            }
            {animalList && animalList.length === 0 &&
                <div>
                    <h3>Brak zwierzaków na sprzedaż</h3>
                </div>
            }
        </div>
    );
};

export default ProductsPanel;
