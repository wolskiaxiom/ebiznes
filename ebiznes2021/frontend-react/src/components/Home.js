import React, {useEffect, useState} from "react";
import backendUrl from "../constants/constants";
import {useCookies} from "react-cookie";

const Home = () => {
    const authenticateUrl = backendUrl + "/authenticate/google"
    const signOutUrl = backendUrl + "/signOut"

    const [cookies] = useCookies(['email'])

    return (
        <div>
            <div style={{textAlign: "center"}}>
                <h1>Witam w sklepie ze zwierzakami!</h1>
                { cookies.email ? (
                        <div>
                            <span>Jesteś zalogowany jako: {cookies.email}</span>
                            <div style={{textAlign: "center", padding: "20px"}}>
                                <a href={signOutUrl}>
                                    Kliknij aby wylogować
                                </a>
                            </div>
                        </div>
                    ) : (
                    <a href={authenticateUrl} className="provider google" title="Google">
                        <img src={"img/google2.png"} alt="Google" width="64px" height="64px"/>
                    </a>
                )}
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