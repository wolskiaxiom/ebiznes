import React from 'react';


const RemoveButton = ({ label, onClick }) => (
    <button onClick={onClick} style={{color: "red"}}>
        {label}
    </button>
);

export default RemoveButton;
