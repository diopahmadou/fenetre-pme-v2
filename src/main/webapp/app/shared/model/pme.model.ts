import { Moment } from 'moment';
import { IContactPme } from 'app/shared/model//contact-pme.model';
import { IPret } from 'app/shared/model//pret.model';
import { IProjet } from 'app/shared/model//projet.model';
import { IImf } from 'app/shared/model//imf.model';

export const enum FormeJuridique {
    SARL = 'SARL',
    SURL = 'SURL',
    GIE = 'GIE',
    INFORMEL = 'INFORMEL'
}

export interface IPme {
    id?: number;
    numeroDossier?: number;
    pmeName?: string;
    formeJuridique?: FormeJuridique;
    dateCreation?: Moment;
    dateEnregistrement?: Moment;
    type?: string;
    activite?: string;
    clientel?: string;
    caPasse?: number;
    caPrevu?: number;
    personneContacts?: IContactPme[];
    prets?: IPret[];
    projets?: IProjet[];
    imfGestionnaire?: IImf;
}

export class Pme implements IPme {
    constructor(
        public id?: number,
        public numeroDossier?: number,
        public pmeName?: string,
        public formeJuridique?: FormeJuridique,
        public dateCreation?: Moment,
        public dateEnregistrement?: Moment,
        public type?: string,
        public activite?: string,
        public clientel?: string,
        public caPasse?: number,
        public caPrevu?: number,
        public personneContacts?: IContactPme[],
        public prets?: IPret[],
        public projets?: IProjet[],
        public imfGestionnaire?: IImf
    ) {}
}
