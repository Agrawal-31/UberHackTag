import React, { useEffect, useState, useRef } from "react";
import axios from "axios";

import {
  API_BASE_URL,
  ACCESS_TOKEN_NAME,
  GOOGLE_MAP_API_KEY,
} from "../../constants/apiConstants";
import Autocomplete from "react-google-autocomplete";
import { Row,Col,Card, Form, InputGroup, Button, FormControl ,Alert} from "react-bootstrap";
import Geosuggest from "react-geosuggest";
import setDefaultAxios from "../../utils/setDefaultAxios";
export default function Home() {
  const [deliveryLocation, setDeliveryLocation] = useState(null);
  const [hubLocation, setHubLocation] = useState(null);
  const packageIdRef = useRef("");
  const packageValueRef = useRef("");
  const dateRef = useRef("");
  const [packages, SetPackages] = useState(null);
 const [loading,setLoading] = useState(false);
  const [error, setError] = useState("");

  async function checkUser() {
    try {
      setDefaultAxios(localStorage.getItem(ACCESS_TOKEN_NAME));
    } catch (err) {}
  }

  useEffect(() => {

  }, []);
  return (

    <div>
      {/*<Geosuggest />*/}
      {/*<h1>Add Packages</h1>*/}

      <Card className="mt-5" bg ="secondary" text = "white">

        <Card.Header>
          <h1 className="text-center">Add Package</h1>
        </Card.Header>
        {error && <Alert className = "text-center" variant="danger">{error}</Alert>}
        <Card.Body>
          <Form>
            <InputGroup  className="mt-4 mb-4" size="lg">
              <InputGroup.Prepend>
                <InputGroup.Text  style={{ width: "180px" }} id="package_id">
                  Package ID
                </InputGroup.Text>
              </InputGroup.Prepend>
              <FormControl  type = "text" ref = {packageIdRef} required/>
            </InputGroup>

            <div id="deliveryLocation">
              <InputGroup className="mt-4 mb-4" size="lg">
                <InputGroup.Prepend>
                  <InputGroup.Text
                    style={{ width: "180px" }}
                    id="deliveryLocation"
                  >
                    Delivery Location
                  </InputGroup.Text>
                </InputGroup.Prepend>
                <Autocomplete
                  style={{ width: "300px" }}
                  onPlaceSelected={(place) => {
                    setDeliveryLocation(place);
                  }}
                  componentRestrictions={{ country: "in" }}
                />
              </InputGroup>
            </div>
            <div id="deliveryLocation">
              <InputGroup className="mt-4 mb-4" size="lg">
                <InputGroup.Prepend>
                  <InputGroup.Text style={{ width: "180px" }} id="hubLocation">
                    Hub Location{" "}
                  </InputGroup.Text>
                </InputGroup.Prepend>

                <Autocomplete
                  style={{ width: "300px" }}
                  onPlaceSelected={(place) => {
                    setHubLocation(place);
                  }}
                />
              </InputGroup>
            </div>

            <InputGroup className="mt-4 mb-4" size="lg">
              <InputGroup.Prepend>
                <InputGroup.Text style={{ width: "180px" }} id="package_value">
                  Package Value
                </InputGroup.Text>
              </InputGroup.Prepend>
              <Form.Control as="select" ref = {packageValueRef} required >
                <option>Low</option>
                <option>Medium</option>
                <option>High</option>
              </Form.Control>
            </InputGroup>

            <InputGroup className="mt-4 mb-4" size="lg">
              <InputGroup.Prepend>
                <InputGroup.Text style={{ width: "180px" }} id="expected_date">
                  Expected Date
                </InputGroup.Text>
              </InputGroup.Prepend>
              <FormControl type="datetime-local" ref = {dateRef} required/>
            </InputGroup>
            <Button disabled={loading} size="lg" className="w-100" variant="dark" type="submit">
              Submit
            </Button>
          </Form>

        </Card.Body>
      </Card>
    </div>
  );
}
