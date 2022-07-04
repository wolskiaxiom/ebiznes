import React from "react";
import animalCategories from "../constants/animal-categories.json"
import NavigationItem from "./NavigationItem";
import {Container, Navbar} from "react-bootstrap";
import {Link} from "react-router-dom";

const Header = () => {
    return (
        <Navbar bg="dark" variant="dark" style={{height: 80}}>
            <Container>
                <Navbar.Brand className={"bg-primary"} style={{padding: "10px"}}>
                    <div>
                        <Link to={"/"} class={"text-light"} style={{ textDecoration: 'none', fontWeight: 600}}>
                            Strona domowa
                        </Link>
                    </div>
                </Navbar.Brand>
                <div style={{padding: "20px", display: "inline-block"}}/>
                {animalCategories.map(a => (
                    <Navbar.Brand className={"bg-secondary"} style={{padding: "10px"}} key={a.animalType}>
                        <NavigationItem animal={a} key={a.animalType}/>
                    </Navbar.Brand>
                ))}
                <div style={{padding: "20px", display: "inline-block"}}/>
                <Navbar.Brand className={"bg-warning"} style={{padding: "10px"}}>
                    <div>
                        <Link class={"text-dark"} style={{ textDecoration: 'none', fontWeight: 600}} to={"/cart"}>Koszyk</Link>
                    </div>
                </Navbar.Brand>

            </Container>
        </Navbar>
    )
}

export default Header