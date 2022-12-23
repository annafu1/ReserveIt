import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/food-menu">
        <Translate contentKey="global.menu.entities.foodMenu" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/restaurant">
        <Translate contentKey="global.menu.entities.restaurant" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/restaurant-table">
        <Translate contentKey="global.menu.entities.restaurantTable" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/customer">
        <Translate contentKey="global.menu.entities.customer" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/reservation">
        <Translate contentKey="global.menu.entities.reservation" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
