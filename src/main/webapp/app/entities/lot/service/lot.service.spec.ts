import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { Unit } from 'app/entities/enumerations/unit.model';
import { Sowing } from 'app/entities/enumerations/sowing.model';
import { Transplantation } from 'app/entities/enumerations/transplantation.model';
import { HarTime } from 'app/entities/enumerations/har-time.model';
import { ILot, Lot } from '../lot.model';

import { LotService } from './lot.service';

describe('Lot Service', () => {
  let service: LotService;
  let httpMock: HttpTestingController;
  let elemDefault: ILot;
  let expectedResult: ILot | ILot[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LotService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      lotCode: 'AAAAAAA',
      lotQRCodeContentType: 'image/png',
      lotQRCode: 'AAAAAAA',
      lotSize: 0,
      unitType: Unit.Cavities,
      seedlingsGerminated: 0,
      seedlingsDied: 0,
      plantsWasted: 0,
      traysSown: 0,
      sowingTime: Sowing.Daily,
      traysTranplanted: 0,
      transplantationTime: Transplantation.Daily,
      plantsHarvested: 0,
      harvestTime: HarTime.Daily,
      packingDate: currentDate,
      tranportationDate: currentDate,
      createdBy: 0,
      createdOn: currentDate,
      updatedBy: 0,
      updatedOn: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          packingDate: currentDate.format(DATE_TIME_FORMAT),
          tranportationDate: currentDate.format(DATE_TIME_FORMAT),
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Lot', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          packingDate: currentDate.format(DATE_TIME_FORMAT),
          tranportationDate: currentDate.format(DATE_TIME_FORMAT),
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          packingDate: currentDate,
          tranportationDate: currentDate,
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.create(new Lot()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Lot', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          lotCode: 'BBBBBB',
          lotQRCode: 'BBBBBB',
          lotSize: 1,
          unitType: 'BBBBBB',
          seedlingsGerminated: 1,
          seedlingsDied: 1,
          plantsWasted: 1,
          traysSown: 1,
          sowingTime: 'BBBBBB',
          traysTranplanted: 1,
          transplantationTime: 'BBBBBB',
          plantsHarvested: 1,
          harvestTime: 'BBBBBB',
          packingDate: currentDate.format(DATE_TIME_FORMAT),
          tranportationDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          packingDate: currentDate,
          tranportationDate: currentDate,
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Lot', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          lotCode: 'BBBBBB',
          lotQRCode: 'BBBBBB',
          lotSize: 1,
          seedlingsGerminated: 1,
          seedlingsDied: 1,
          transplantationTime: 'BBBBBB',
          plantsHarvested: 1,
          harvestTime: 'BBBBBB',
          packingDate: currentDate.format(DATE_TIME_FORMAT),
          tranportationDate: currentDate.format(DATE_TIME_FORMAT),
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new Lot()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          packingDate: currentDate,
          tranportationDate: currentDate,
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Lot', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          lotCode: 'BBBBBB',
          lotQRCode: 'BBBBBB',
          lotSize: 1,
          unitType: 'BBBBBB',
          seedlingsGerminated: 1,
          seedlingsDied: 1,
          plantsWasted: 1,
          traysSown: 1,
          sowingTime: 'BBBBBB',
          traysTranplanted: 1,
          transplantationTime: 'BBBBBB',
          plantsHarvested: 1,
          harvestTime: 'BBBBBB',
          packingDate: currentDate.format(DATE_TIME_FORMAT),
          tranportationDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          packingDate: currentDate,
          tranportationDate: currentDate,
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Lot', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLotToCollectionIfMissing', () => {
      it('should add a Lot to an empty array', () => {
        const lot: ILot = { id: 123 };
        expectedResult = service.addLotToCollectionIfMissing([], lot);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(lot);
      });

      it('should not add a Lot to an array that contains it', () => {
        const lot: ILot = { id: 123 };
        const lotCollection: ILot[] = [
          {
            ...lot,
          },
          { id: 456 },
        ];
        expectedResult = service.addLotToCollectionIfMissing(lotCollection, lot);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Lot to an array that doesn't contain it", () => {
        const lot: ILot = { id: 123 };
        const lotCollection: ILot[] = [{ id: 456 }];
        expectedResult = service.addLotToCollectionIfMissing(lotCollection, lot);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(lot);
      });

      it('should add only unique Lot to an array', () => {
        const lotArray: ILot[] = [{ id: 123 }, { id: 456 }, { id: 51473 }];
        const lotCollection: ILot[] = [{ id: 123 }];
        expectedResult = service.addLotToCollectionIfMissing(lotCollection, ...lotArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const lot: ILot = { id: 123 };
        const lot2: ILot = { id: 456 };
        expectedResult = service.addLotToCollectionIfMissing([], lot, lot2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(lot);
        expect(expectedResult).toContain(lot2);
      });

      it('should accept null and undefined values', () => {
        const lot: ILot = { id: 123 };
        expectedResult = service.addLotToCollectionIfMissing([], null, lot, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(lot);
      });

      it('should return initial array if no Lot is added', () => {
        const lotCollection: ILot[] = [{ id: 123 }];
        expectedResult = service.addLotToCollectionIfMissing(lotCollection, undefined, null);
        expect(expectedResult).toEqual(lotCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
