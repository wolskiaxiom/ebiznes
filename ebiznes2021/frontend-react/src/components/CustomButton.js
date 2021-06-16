import React from 'react';


const CustomButton = ({ label, onClick }) => (
    <button onClick={onClick}>
        {label}
    </button>
);

export default CustomButton;
