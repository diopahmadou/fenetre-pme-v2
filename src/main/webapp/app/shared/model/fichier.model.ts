import { IPret } from 'app/shared/model//pret.model';

export interface IFichier {
    id?: number;
    fileName?: string;
    fileType?: string;
    dataContentType?: string;
    data?: any;
    pret?: IPret;
}

export class Fichier implements IFichier {
    constructor(
        public id?: number,
        public fileName?: string,
        public fileType?: string,
        public dataContentType?: string,
        public data?: any,
        public pret?: IPret
    ) {}
}
