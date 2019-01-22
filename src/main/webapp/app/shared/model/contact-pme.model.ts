import { IPme } from 'app/shared/model//pme.model';

export interface IContactPme {
    id?: number;
    prenom?: string;
    nom?: string;
    email?: string;
    telephone?: string;
    fonction?: string;
    adresse?: string;
    pme?: IPme;
}

export class ContactPme implements IContactPme {
    constructor(
        public id?: number,
        public prenom?: string,
        public nom?: string,
        public email?: string,
        public telephone?: string,
        public fonction?: string,
        public adresse?: string,
        public pme?: IPme
    ) {}
}
