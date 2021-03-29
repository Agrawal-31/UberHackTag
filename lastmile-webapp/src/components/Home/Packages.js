import React, {useEffect, useState} from "react";
import axios from "axios";
import {API_BASE_URL,ACCESS_TOKEN_NAME} from "../../constants/apiConstants";
import {Card, Alert, Row, Col, Badge, Container} from "react-bootstrap"
import setDefaultAxios from "../../utils/setDefaultAxios";
export default function ViewPackage() {
    const [packages, setPackages] = useState(null);

    function updatePackages() {
        axios.get(
            API_BASE_URL + "/packages/"
        )
            .then(function (response) {
                const data = response.data;
                if(data.error || data.errors){
                    console.log(data);
                }
                setPackages(data);
            })
            .catch(function (error) {
                console.log(error);
            })
            .then(function () {
                // always executed
            });
    }

    async function checkUser() {
        try {
            setDefaultAxios(localStorage.getItem(ACCESS_TOKEN_NAME));
        } catch (err) {}
    }

    useEffect(() => {
        checkUser().then(() => updatePackages());
    }, []);

    const RenderPackageList = () =>{
    if(!packages){
        return <h1>Loading</h1>
    }

        const p = [];
        if (packages.length === 0) {

            p.push(<Alert variant="primary">No Packages yet.</Alert>);
            return p;
        }

        packages.forEach((id) =>{
            p.push(
                <>
                    <Card body className="p-1 mt-4 align-items-center justify-content-center"  key = {id.packageId}>
                        <Row>
                            <Col md="auto">
                               <h4 > Package Number {id.packageId}</h4>
                                <br/>
                                <h5 >Hub Location {id.hubLocation}</h5>
                                <br/>
                                <h5> Delivery Location  {id.deliveryLocation}</h5>
                                <br/>
                                <h5> Package Value {id.packageValue}</h5>
                                <br/>
                                <hr />
                            </Col>
                        </Row>
                    </Card>
                </>
            )
        })
        return (
            <>
                {p}
            </>
        );

    }
    return (
        <div className= "w-40 d-flex align-items-center justify-content-center">


            <Container className= "align-items-center justify-content-center" >

                <h2 className="text-center mt-4">View Packages</h2>
                {RenderPackageList()}
            </Container>
            </div>


    );
}
