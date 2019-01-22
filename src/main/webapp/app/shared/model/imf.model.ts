import { IPme } from 'app/shared/model//pme.model';

export interface IImf {
    id?: number;
    agrement?: string;
    nom?: string;
    siegeSocial?: string;
    registreCommerce?: string;
    ninea?: string;
    secteurActivite?: string;
    formeJuridique?: string;
    capital?: number;
    adresseAgence?: string;
    telephone?: string;
    email?: string;
    pmeGeres?: IPme[];
}

export class Imf implements IImf {
    constructor(
        public id?: number,
        public agrement?: string,
        public nom?: string,
        public siegeSocial?: string,
        public registreCommerce?: string,
        public ninea?: string,
        public secteurActivite?: string,
        public formeJuridique?: string,
        public capital?: number,
        public adresseAgence?: string,
        public telephone?: string,
        public email?: string,
        public pmeGeres?: IPme[]
    ) {}
}
