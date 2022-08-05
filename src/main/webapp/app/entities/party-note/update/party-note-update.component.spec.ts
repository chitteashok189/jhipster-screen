import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PartyNoteService } from '../service/party-note.service';
import { IPartyNote, PartyNote } from '../party-note.model';
import { IParty } from 'app/entities/party/party.model';
import { PartyService } from 'app/entities/party/service/party.service';

import { PartyNoteUpdateComponent } from './party-note-update.component';

describe('PartyNote Management Update Component', () => {
  let comp: PartyNoteUpdateComponent;
  let fixture: ComponentFixture<PartyNoteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let partyNoteService: PartyNoteService;
  let partyService: PartyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PartyNoteUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PartyNoteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyNoteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    partyNoteService = TestBed.inject(PartyNoteService);
    partyService = TestBed.inject(PartyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Party query and add missing value', () => {
      const partyNote: IPartyNote = { id: 456 };
      const partyID: IParty = { id: 56440 };
      partyNote.partyID = partyID;

      const partyCollection: IParty[] = [{ id: 38092 }];
      jest.spyOn(partyService, 'query').mockReturnValue(of(new HttpResponse({ body: partyCollection })));
      const additionalParties = [partyID];
      const expectedCollection: IParty[] = [...additionalParties, ...partyCollection];
      jest.spyOn(partyService, 'addPartyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ partyNote });
      comp.ngOnInit();

      expect(partyService.query).toHaveBeenCalled();
      expect(partyService.addPartyToCollectionIfMissing).toHaveBeenCalledWith(partyCollection, ...additionalParties);
      expect(comp.partiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const partyNote: IPartyNote = { id: 456 };
      const partyID: IParty = { id: 16550 };
      partyNote.partyID = partyID;

      activatedRoute.data = of({ partyNote });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(partyNote));
      expect(comp.partiesSharedCollection).toContain(partyID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyNote>>();
      const partyNote = { id: 123 };
      jest.spyOn(partyNoteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyNote });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyNote }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(partyNoteService.update).toHaveBeenCalledWith(partyNote);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyNote>>();
      const partyNote = new PartyNote();
      jest.spyOn(partyNoteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyNote });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyNote }));
      saveSubject.complete();

      // THEN
      expect(partyNoteService.create).toHaveBeenCalledWith(partyNote);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyNote>>();
      const partyNote = { id: 123 };
      jest.spyOn(partyNoteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyNote });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(partyNoteService.update).toHaveBeenCalledWith(partyNote);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPartyById', () => {
      it('Should return tracked Party primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPartyById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
