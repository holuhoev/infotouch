import { values } from 'ramda'

export const selectBuildings = state => values(state.buildings);