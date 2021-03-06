import React from 'react';
import useFetch from '../hooks/useFetch';
import Animal from './Animal';


const AnimalProductPanel = ({ animal }) => {
    const { response: animalList, loading, error } = useFetch(`https://ebiznes-backend.azurewebsites.net${animal.url}`, []);
    return (
        <div>
            <h1>{animal.categoryName}</h1>
            {loading && <p>Loading...</p>}
            {error && <p>Something went wrong...</p>}
            {animalList && animalList.length > 0 &&
                <ul>
                    {animalList.map(a => (
                        <Animal item={a} key={a.price}/>
                    ))}
                </ul>
            }
            {animalList && animalList.length === 0 &&
                <div>
                    <h3>Brak zwierzaków na sprzedaż z kategorii: {animal.categoryName}</h3>
                </div>
            }
        </div>
    )
}

export default AnimalProductPanel;
