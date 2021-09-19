import React from "react";
import animalCategories from "../constants/animal-categories.json"
import NavigationItem from "./NavigationItem";
import {Container, Navbar} from "react-bootstrap";
import {Link} from "react-router-dom";

const Header = () => {
    return (
        <Navbar bg="dark" variant="dark">
            <Container>
                <Navbar.Brand>
                    <div style={{padding: "20px", display: "inline-block", background: "red"}}>
                        <Link to={"/"}>Strona domowa</Link>
                    </div>
                </Navbar.Brand>
                <div style={{padding: "20px", display: "inline-block"}}/>
                {animalCategories.map(a => (
                    <Navbar.Brand>
                        <NavigationItem animal={a} key={a.animalType}/>
                    </Navbar.Brand>
                ))}
                <div style={{padding: "20px", display: "inline-block"}}/>
                <Navbar.Brand>
                    <div style={{padding: "20px", display: "inline-block", background: "red"}}>
                        <Link to={"/cart"}>Koszyk</Link>
                    </div>
                </Navbar.Brand>

            </Container>
        </Navbar>
    )
}

export default Header