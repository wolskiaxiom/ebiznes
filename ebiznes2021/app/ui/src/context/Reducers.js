export const cartReducer = (state, action) => {
    switch (action.type) {
        case "ADD_TO_CART":
            return {...state, cart:[...state.cart, action.payload] };
        case "REMOVE_FROM_CART":
            return {...state, cart:state.cart.filter(c => c.id !== action.payload.id || c.category !== action.payload.category), };
        case "CLEAN_CART":
            return {...state, cart:[]}
        default:
            return state;
    }
}