import React from "react";
import backendUrl from "../constants/constants";
import {useCookies} from "react-cookie";
import {Button} from "react-bootstrap";
import axios from "axios";

const Home = () => {
    const authenticateUrl = backendUrl + "/authenticate/google"
    const signOutUrl = backendUrl + "/signOut"

    const [cookies, removeCookie] = useCookies(['email'])

    return (
        <div>
            <div style={{textAlign: "center"}}>
                <h1>Witam w sklepie ze zwierzakami!</h1>
                { cookies.email && cookies.email !== "undefined" ? (
                        <div>
                            <span>Jesteś zalogowany jako: {cookies.email}</span>
                            <div style={{textAlign: "center", padding: "20px"}}>
                                 <Button style={{background: "transparent", color: "black"}} onClick={ () => {
                                     const logout = async () => {
                                         await axios.get(signOutUrl).then(response => {
                                             if (response.status === 200) {
                                                 alert("Wylogowano pomyślnie")
                                             }
                                         }).catch(() => {
                                             alert("Wylogowano pomyślnie...")
                                         });
                                     };
                                     logout();
                                     removeCookie('email')
                                 }}>
                                     Wyloguj
                                 </Button>
                            </div>
                        </div>
                    ) : (
                    <a href={authenticateUrl} className="provider google" title="Google">
                        <img src={"img/google2.png"} alt="Google" width="64px" height="64px"/>
                    </a>
                )}
            </div>
            <div style={{textAlign: "center", paddingTop: "30px"}}>
                <img src={"img/logo-shop.png"} style={{alignContent: "center"}} alt={"Friendly dogo here!"}/>
            </div>
        </div>
    )
};

export default Home