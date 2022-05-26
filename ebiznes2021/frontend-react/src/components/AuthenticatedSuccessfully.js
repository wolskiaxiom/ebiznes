import React, {useEffect} from "react";
import {useCookies} from "react-cookie";

const AuthenticatedSuccessfully = () => {
    const [cookies] = useCookies(['email'])

    return (
        <div style={{textAlign: "center", paddingTop: 100}}>
            {cookies.email ? (
                <>
                    Zostałeś pomyślnie zalogowany jako: {cookies.email}

                </>
            ) : (
                <>
                    Nie jesteś zalogowany
                </>
            )}
        </div>
    );
};

export default AuthenticatedSuccessfully;
