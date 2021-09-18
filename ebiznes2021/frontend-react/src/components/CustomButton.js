import React from 'react';


const CustomButton = ({ label, onClick, disabled }) => (
    <button onClick={onClick} disabled={disabled}>
        {label}
    </button>
);

export default CustomButton;
