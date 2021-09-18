import React from "react";
import Shop from "./components/Shop";
import Header from "./components/Header";
import Home from "./components/Home";
import {BrowserRouter, Route} from "react-router-dom";
import Cart from "./components/Cart";
import animalCategories from "./constants/animal-categories.json"

function App() {
    console.log(animalCategories.filter(c => c.animalType === "CAT"))
    return (
        <BrowserRouter>
            <Header/>
            <div>
                <Route path={"/"} exact>
                    <Home/>
                </Route>
                <Route path={"/cart"} exact>
                    <Cart/>
                </Route>

                <Route path={"/cats"} exact>
                    <Shop animal={animalCategories.filter(c => c.animalType === "CAT")[0]}/>
                </Route>

                <Route path={"/dogs"} exact>
                    <Shop animal={animalCategories.filter(c => c.animalType === "DOG")[0]}/>
                </Route>

                <Route path={"/snakes"} exact>
                    <Shop animal={animalCategories.filter(c => c.animalType === "SNAKE")[0]}/>
                </Route>

                <Route path={"/parrots"} exact>
                    <Shop animal={animalCategories.filter(c => c.animalType === "PARROT")[0]}/>
                </Route>

                <Route path={"/pigs"} exact>
                    <Shop animal={animalCategories.filter(c => c.animalType === "PIG")[0]}/>
                </Route>

                <Route path={"/hamsters"} exact>
                    <Shop animal={animalCategories.filter(c => c.animalType === "HAMSTER")[0]}/>
                </Route>

                <Route path={"/spiders"} exact>
                    <Shop animal={animalCategories.filter(c => c.animalType === "SPIDER")[0]}/>
                </Route>
            </div>
        </BrowserRouter>
    );
}

export default App;
