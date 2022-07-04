import React from 'react';


const CustomButton = ({ label, onClick, disabled }) => (
    <button class="btn btn-primary" onClick={onClick} disabled={disabled}>
        {label}
    </button>
);

export default CustomButton;
