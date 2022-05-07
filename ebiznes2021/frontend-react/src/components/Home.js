import React from "react";

const Home = () => {
    return (
        <div>
            <div style={{textAlign: "center"}}>
                <h1>Witam w sklepie ze zwierzakami!</h1>
                <a href="https://ebiznes-backend.azurewebsites.net/authenticate/google" className="provider google" title="Google">
                    <img src={"img/google2.png"} alt="Google" width="64px" height="64px"/>
                </a>
            </div>
            <div style={{textAlign: "center", padding: "20px"}}>
                <a href="https://ebiznes-backend.azurewebsites.net/signOut">
                    Kliknij aby wylogować
                </a>
            </div>
            <div style={{textAlign: "center", padding: "20px"}}>
                <a href="https://ebiznes-backend.azurewebsites.net/">
                    Polacz z zabezpieczonym endpointem
                </a>
            </div>
            <div style={{textAlign: "center"}}>
                <img src={"img/logo-shop.png"} style={{alignContent: "center"}} alt={"Friendly dogo here!"}/>
            </div>
        </div>
    )
};

export default Home