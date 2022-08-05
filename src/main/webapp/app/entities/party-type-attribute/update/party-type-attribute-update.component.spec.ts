import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PartyTypeAttributeService } from '../service/party-type-attribute.service';
import { IPartyTypeAttribute, PartyTypeAttribute } from '../party-type-attribute.model';

import { PartyTypeAttributeUpdateComponent } from './party-type-attribute-update.component';

describe('PartyTypeAttribute Management Update Component', () => {
  let comp: PartyTypeAttributeUpdateComponent;
  let fixture: ComponentFixture<PartyTypeAttributeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let partyTypeAttributeService: PartyTypeAttributeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PartyTypeAttributeUpdateComponent],
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
      .overrideTemplate(PartyTypeAttributeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyTypeAttributeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    partyTypeAttributeService = TestBed.inject(PartyTypeAttributeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const partyTypeAttribute: IPartyTypeAttribute = { id: 456 };

      activatedRoute.data = of({ partyTypeAttribute });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(partyTypeAttribute));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyTypeAttribute>>();
      const partyTypeAttribute = { id: 123 };
      jest.spyOn(partyTypeAttributeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyTypeAttribute });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyTypeAttribute }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(partyTypeAttributeService.update).toHaveBeenCalledWith(partyTypeAttribute);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyTypeAttribute>>();
      const partyTypeAttribute = new PartyTypeAttribute();
      jest.spyOn(partyTypeAttributeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyTypeAttribute });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyTypeAttribute }));
      saveSubject.complete();

      // THEN
      expect(partyTypeAttributeService.create).toHaveBeenCalledWith(partyTypeAttribute);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyTypeAttribute>>();
      const partyTypeAttribute = { id: 123 };
      jest.spyOn(partyTypeAttributeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyTypeAttribute });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(partyTypeAttributeService.update).toHaveBeenCalledWith(partyTypeAttribute);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
