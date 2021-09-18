import {CartState} from "../context/Context";
import {useEffect, useState} from "react";
import {Button, Col, Container, Form, ListGroup, Row} from "react-bootstrap";
import {AiFillDelete} from "react-icons/all";
import "./styles.css";
import axios from "axios";

const Cart = () => {
    const {state: {cart}, dispatch} = CartState()

    const [email, setEmail] = useState("")
    const [nick, setNick] = useState("")
    const [address1, setAddress1] = useState("")
    const [address2, setAddress2] = useState("")
    const [city, setCity] = useState("")
    const [zipcode, setZipcode] = useState("")

    const [total, setTotal] = useState()

    useEffect(() => {
        console.log(cart)
        setTotal(cart.reduce((acc, curr) => acc + Number(curr.price), 0))
    }, [cart])
    return (
        <div className={"home"}>
            <div className={"productContainer"}>
                {cart.length > 0 ? (<div>
                    <ListGroup>
                        {
                            cart.map((prod) => (
                                <ListGroup.Item variant={"dark"}
                                                key={(prod.id.toString() + prod.animalType.toString()).split("").reduce(function (a, b) {
                                                    a = ((a << 5) - a) + b.charCodeAt(0);
                                                    return a & a
                                                }, 0)}>
                                    <Container styles={{background: "#61dafb"}}>
                                        <Row>
                                            <Col>
                                                <h3>{prod.animalType}</h3>
                                            </Col>
                                            <Col> Imię: "{prod.name}" </Col>
                                        </Row>

                                        <Row>
                                            <Col> Cena: {prod.price} PLN </Col>
                                            <Col> Wiek: "{prod.age}" </Col>
                                            <Col>
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
                    <>
                        <Form onSubmit={(event => {event.preventDefault()})}>
                            <Row className="mb-3">
                                <Form.Group as={Col} controlId="formGridEmail">
                                    <Form.Label >Email: </Form.Label>
                                    <Form.Control size={"lg"} type="email" placeholder="Email"
                                                  onChange={(event) => {
                                                      setEmail(event.target.value)
                                                  }}
                                    />
                                </Form.Group>

                                <Form.Group as={Col} controlId="nickname">
                                    <Form.Label>Imię: </Form.Label>
                                    <Form.Control type="text" placeholder="Imię"
                                                  onChange={(event) => {
                                                      setNick(event.target.value);
                                                  }}
                                    />
                                </Form.Group>
                            </Row>

                            <Form.Group className="mb-3" controlId="formGridAddress1">
                                <Form.Label>Adres: </Form.Label>
                                <Form.Control placeholder="Ulica i numer"
                                              onChange={(event) => {
                                                  setAddress1(event.target.value);
                                              }}
                                />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formGridAddress2">
                                <Form.Label>Adres: </Form.Label>
                                <Form.Control placeholder="Dodatkowe informacje"
                                              onChange={(event) => {
                                                  setAddress2(event.target.value);
                                              }}
                                />
                            </Form.Group>

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
                                <Button
                                    className="d-grid gap-2"
                                    type={"submit"}
                                    size={"lg"}
                                    disabled={cart.length < 1}
                                    onClick={() => {
                                        const postOrders = async () => {
                                            let response = await axios.post('http://localhost:12345/order', {
                                                customer_email: email.toString(),
                                                customer_nick: nick.toString(),
                                                customer_address1: address1.toString(),
                                                customer_address2: address2.toString(),
                                                customer_city: city.toString(),
                                                customer_zipcode: zipcode.toString(),
                                                total_price: total,
                                                comments: 'commented',
                                                order_items: [...Array(cart.length).keys()]
                                                    .map((i) => {
                                                        return {
                                                            item_id: cart[i].id,
                                                            item_num: cart[i].qty
                                                        }
                                                    }),

                                            });
                                            if (response.status === 200) {
                                                dispatch({
                                                    type: "CLEAN_CART",
                                                    payload: {},
                                                })
                                            }
                                        };

                                        postOrders();
                                    }}
                                >
                                    Wyślij zapytanie o dostępność
                                </Button>
                            </Row>
                        </Form>
                    </>
                </div>
            </div>
        </div>
    )

}
export default Cart;