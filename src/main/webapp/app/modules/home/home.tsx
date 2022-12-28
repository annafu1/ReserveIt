import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="10" className="pad">
        <span className="hipster rounded" />
      </Col>
      {/*       <Col md="10"> */}
      {/*         <h2> */}
      {/*           <Translate contentKey="home.title">Welcome to Reserve It!</Translate> */}
      {/*         </h2> */}
      <div id="reserveButton">
        <Link to="/reservation">
          <button className="btn btn-primary">Reserve a table</button>
        </Link>
      </div>
      {/*       </Col> */}
    </Row>
  );
};

export default Home;
