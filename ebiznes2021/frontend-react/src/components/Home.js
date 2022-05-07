import React from "react";
import backendUrl from "../constants/constants";

const Home = () => {
    const authenticateUrl = backendUrl + "/authenticate/google"
    const signOutUrl = backendUrl + "/signOut"
    return (
        <div>
            <div style={{textAlign: "center"}}>
                <h1>Witam w sklepie ze zwierzakami!</h1>
                <a href={authenticateUrl} className="provider google" title="Google">
                    <img src={"img/google2.png"} alt="Google" width="64px" height="64px"/>
                </a>
            </div>
            <div style={{textAlign: "center", padding: "20px"}}>
                <a href={signOutUrl}>
                    Kliknij aby wylogować
                </a>
            </div>
            <div style={{textAlign: "center", padding: "20px"}}>
                <a href={backendUrl}>
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