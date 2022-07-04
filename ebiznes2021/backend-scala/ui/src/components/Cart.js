import {CartState} from "../context/Context";
import {useEffect, useState} from "react";
import {Button, Col, Container, Form, ListGroup, Row} from "react-bootstrap";
import {AiFillDelete} from "react-icons/all";
import "./styles.css";
import axios from "axios";
import backendUrl from "../constants/constants";

const Cart = () => {
    const {state, dispatch} = CartState()
    const cart = state.cart;

    const [nick, setNick] = useState("")
    const [address1, setAddress1] = useState("")
    const [city, setCity] = useState("")
    const [zipcode, setZipcode] = useState("")

    const [total, setTotal] = useState()

    useEffect(() => {
        setTotal(cart.reduce((acc, curr) => acc + Number(curr.price), 0))
    }, [cart])
    return (
        <div className={"home"}>
            <div className={"productContainer"}>
                {cart.length > 0 ? (<div>
                    <ListGroup >
                        {
                            cart.map((prod) => (
                                <ListGroup.Item variant={"dark"}
                                                key={(prod.id.toString() + prod.category.toString()).split("").reduce(function (a, b) {
                                                    a = ((a << 5) - a) + b.charCodeAt(0);
                                                    return a & a
                                                }, 0)}>
                                    <Container fluid styles={{background: "#61dafb"}} >
                                        <Row>
                                            <Col>
                                                <h4>{prod.animalType}</h4>
                                            </Col>
                                        </Row>
                                        <Row>
                                            <Col> Imię: "{prod.name}" </Col>
                                        </Row>

                                        <Row>
                                            <Col> Cena: {prod.price} PLN </Col>
                                        </Row>
                                        <Row>
                                            <Col >
                                                <Button
                                                    type={"button"}
                                                    variant={"light"}
                                                    onClick={() =>
                                                        dispatch({
                                                            type: "REMOVE_FROM_CART",
                                                            payload: prod,
                                                        })
                                                    }>
                                                    <AiFillDelete fontSize={"20px"}/>
                                                </Button>
                                            </Col>
                                        </Row>
                                    </Container>
                                </ListGroup.Item>
                            ))
                        }
                    </ListGroup>

                </div>) : <div style={{textAlign: "center", padding: "30px"}}>
                    <span>Brak produktow w koszyku</span>
                </div>}
            </div>

            <div className={"filters summary"}>
                <span style={{fontWeight: 700, fontSize: 20}}>Szacowana cena: {total} PLN</span>
                <div style={{height: 100}}/>
                <div style={{textAlign: "center"}}>
                    <span style={{fontWeight: 60, fontSize: 17}}>Wprowadź dane do zapytania</span>
                    <Form onSubmit={(event => {
                        event.preventDefault()
                    })}>
                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="nickname">
                                <Form.Label>Imię: </Form.Label>
                                <Form.Control type="text" placeholder="Imię i nazwisko"
                                              onChange={(event) => {
                                                  setNick(event.target.value);
                                              }}
                                />
                            </Form.Group>
                        </Row>
                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="formGridAddress1">
                                <Form.Label>Adres: </Form.Label>
                                <Form.Control value={address1}
                                              onChange={(event) => {
                                                  setAddress1(event.target.value);
                                              }}
                                />
                            </Form.Group>
                        </Row>

                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="formGridCity">
                                <Form.Label>Miasto: </Form.Label>
                                <Form.Control
                                    onChange={(event) => {
                                        setCity(event.target.value);
                                    }}
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId="formGridZip">
                                <Form.Label>Kod pocztowy: </Form.Label>
                                <Form.Control
                                    onChange={(event) => {
                                        setZipcode(event.target.value);
                                    }}
                                />
                            </Form.Group>
                        </Row>

                        <Row className={"mb-2"}>
                            <Form.Group as={Col} controlId="formGridCity">
                            <Button
                                className="d-grid gap-2 text-center"
                                type={"submit"}
                                size={"lg"}
                                disabled={cart.length < 1}
                                onClick={() => {
                                    const postOrders = async () => {
                                        await axios.post(backendUrl + '/order', {
                                            customer_nick: nick.toString(),
                                            customer_address1: address1.toString(),
                                            customer_city: city.toString(),
                                            customer_zipcode: zipcode.toString(),
                                            total_price: total,
                                            order_items: [...Array(cart.length).keys()]
                                                .map((i) => {
                                                    return {
                                                        item_id: cart[i].id,
                                                        item_cat: cart[i].category
                                                    }
                                                }),
                                        }, {
                                            withCredentials: true
                                        }).then(response => {
                                            if (response.status === 200) {
                                                dispatch({
                                                    type: "CLEAN_CART",
                                                    payload: {},
                                                })
                                                alert("Zamówienie zostało złożone!")
                                            }
                                        }).catch(error => {
                                            if (error.response.status === 400) {
                                                alert("Coś poszło nie tak z zamówieniem... Upewnij się, że uzupełniłeś wszystkie pola w formularzu.")
                                            } else if (error.response.status === 401) {
                                                alert("Coś poszło nie tak z zamówieniem... Upewnij się że jesteś zalogowany.")
                                            } else {
                                                alert("Coś poszło nie tak z zamówieniem... Skontaktuj sie z administratorem.")
                                            }
                                        });
                                    };
                                    postOrders();
                                }}
                            >
                                Wyślij zapytanie o dostępność
                            </Button>
                            </Form.Group>
                        </Row>
                    </Form>
                </div>
            </div>
        </div>
    )

}
export default Cart