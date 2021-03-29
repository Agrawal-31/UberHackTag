import React, { useEffect, useState, useRef } from "react";
import axios from "axios";

import {
  API_BASE_URL,
  ACCESS_TOKEN_NAME,
  GOOGLE_MAP_API_KEY,
} from "../../constants/apiConstants";
import Autocomplete from "react-google-autocomplete";
import {Tabs, Image,Row,Col,Card, Form, InputGroup, Button, FormControl ,Alert} from "react-bootstrap";
import Geosuggest from "react-geosuggest";
import setDefaultAxios from "../../utils/setDefaultAxios";
import banner from "../../banner.png"
export default function Home() {
  const [deliveryLocation, setDeliveryLocation] = useState(null);
  const [hubLocation, setHubLocation] = useState(null);
  const packageIdRef = useRef();
  const packageValueRef = useRef();
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

  function addPackage(){
      if(!deliveryLocation){
          setError("Enter Delivery Location");
          return;
      }
      if(!hubLocation){
          setError("Enter Hub Location");
          return;
      }

      const externalPackageId = packageIdRef.current.value;
      if(externalPackageId.length !== 10){
          setError("Package ID Must be 10 Digit")
          return
      }
      const packageValue = packageValueRef.current.value;
      const expectedDeliverydate = dateRef.current.value;
      const deliveryLocationAddress = deliveryLocation.formatted_address;
      const hubLocationAddress = hubLocation.formatted_address;
      const deliveryLocationID = deliveryLocation.place_id;
      const hubLocationID = hubLocation.place_id;
    console.log(deliveryLocationID)

      const payload = {
          packageValue:packageValue,
          hubLocationId:hubLocationID,
          deliveryLocationId:deliveryLocationID,
          hubLocation:hubLocationAddress,
          deliveryLocation:deliveryLocationAddress,
          externalPackageId:externalPackageId,
          expectedDeliveryDate:expectedDeliverydate

      };

      axios.post(
          API_BASE_URL + "/packages/",payload
      )
          .then(function (response) {
              console.log(response);
          })
          .catch(function (error) {
              console.log(error);
          })
          .then(function () {
              // always executed
          });

  }

  async function handleSubmit(e){
      e.preventDefault();
      checkUser().then(() => addPackage());
  }
  function renderAddPackageForm(){
    return(
        <Card className="mt-5" bg ="secondary" text = "white">

          <Card.Header>
            <h1 className="text-center">Add Package</h1>
          </Card.Header>
          {error && <Alert className = "text-center" variant="danger">{error}</Alert>}
          <Card.Body>
            <Form onSubmit={handleSubmit}>
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
                      style={{ width: "315px" }}
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
                      style={{ width: "315px" }}
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
              <Button  size="lg" className="w-100" variant="dark" type = "submit">
                Submit
              </Button>
            </Form>

          </Card.Body>
        </Card>

    );

  }
  return (

    <div>
      {/*<Geosuggest />*/}
      {/*<h1>Add Packages</h1>*/}
  <Row>
  <Col>
    <Image className = "al" src ={banner} fluid></Image>
  </Col>
    <Col>
        {renderAddPackageForm()}
    </Col>
  </Row>
    </div>
  );
}
