import React from 'react';
import useFetch from '../hooks/useFetch';
import Animal from './Animal';
import backendUrl from '../constants/constants';


const AnimalProductPanel = ({ animal }) => {
    const { response: animalList, loading, error } = useFetch(backendUrl + animal.url, []);
    return (
        <div>
            <p style={{padding: 20}} className="h1 text-center ">{animal.categoryName}</p>
            {loading && <p>Loading...</p>}
            {error && <p>Something went wrong...</p>}
            {animalList && animalList.length > 0 &&
                <div className='productContainer'>
                    {animalList.map(a => {
                        return <Animal item={a} category={animal.animalType} key={a.price}/>
                    })}
                </div>
            }
            {animalList && animalList.length === 0 &&
                <div>
                    <p style={{padding: 20}} className="h3 text-center font-italic">Brak zwierzaków na sprzedaż z kategorii: {animal.categoryName}</p>
                </div>
            }
        </div>
    )
}

export default AnimalProductPanel;
