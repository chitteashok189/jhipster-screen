import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PartyService } from '../service/party.service';
import { IParty, Party } from '../party.model';
import { IPartyRole } from 'app/entities/party-role/party-role.model';
import { PartyRoleService } from 'app/entities/party-role/service/party-role.service';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';
import { IApplicationUser } from 'app/entities/application-user/application-user.model';
import { ApplicationUserService } from 'app/entities/application-user/service/application-user.service';
import { IPartyGroup } from 'app/entities/party-group/party-group.model';
import { PartyGroupService } from 'app/entities/party-group/service/party-group.service';
import { IPartyType } from 'app/entities/party-type/party-type.model';
import { PartyTypeService } from 'app/entities/party-type/service/party-type.service';

import { PartyUpdateComponent } from './party-update.component';

describe('Party Management Update Component', () => {
  let comp: PartyUpdateComponent;
  let fixture: ComponentFixture<PartyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let partyService: PartyService;
  let partyRoleService: PartyRoleService;
  let personService: PersonService;
  let applicationUserService: ApplicationUserService;
  let partyGroupService: PartyGroupService;
  let partyTypeService: PartyTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PartyUpdateComponent],
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
      .overrideTemplate(PartyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    partyService = TestBed.inject(PartyService);
    partyRoleService = TestBed.inject(PartyRoleService);
    personService = TestBed.inject(PersonService);
    applicationUserService = TestBed.inject(ApplicationUserService);
    partyGroupService = TestBed.inject(PartyGroupService);
    partyTypeService = TestBed.inject(PartyTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PartyRole query and add missing value', () => {
      const party: IParty = { id: 456 };
      const partyRoleID: IPartyRole = { id: 51423 };
      party.partyRoleID = partyRoleID;

      const partyRoleCollection: IPartyRole[] = [{ id: 59034 }];
      jest.spyOn(partyRoleService, 'query').mockReturnValue(of(new HttpResponse({ body: partyRoleCollection })));
      const additionalPartyRoles = [partyRoleID];
      const expectedCollection: IPartyRole[] = [...additionalPartyRoles, ...partyRoleCollection];
      jest.spyOn(partyRoleService, 'addPartyRoleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ party });
      comp.ngOnInit();

      expect(partyRoleService.query).toHaveBeenCalled();
      expect(partyRoleService.addPartyRoleToCollectionIfMissing).toHaveBeenCalledWith(partyRoleCollection, ...additionalPartyRoles);
      expect(comp.partyRolesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Person query and add missing value', () => {
      const party: IParty = { id: 456 };
      const personID: IPerson = { id: 5652 };
      party.personID = personID;

      const personCollection: IPerson[] = [{ id: 16698 }];
      jest.spyOn(personService, 'query').mockReturnValue(of(new HttpResponse({ body: personCollection })));
      const additionalPeople = [personID];
      const expectedCollection: IPerson[] = [...additionalPeople, ...personCollection];
      jest.spyOn(personService, 'addPersonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ party });
      comp.ngOnInit();

      expect(personService.query).toHaveBeenCalled();
      expect(personService.addPersonToCollectionIfMissing).toHaveBeenCalledWith(personCollection, ...additionalPeople);
      expect(comp.peopleSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ApplicationUser query and add missing value', () => {
      const party: IParty = { id: 456 };
      const applicationUserID: IApplicationUser = { id: 33959 };
      party.applicationUserID = applicationUserID;

      const applicationUserCollection: IApplicationUser[] = [{ id: 69818 }];
      jest.spyOn(applicationUserService, 'query').mockReturnValue(of(new HttpResponse({ body: applicationUserCollection })));
      const additionalApplicationUsers = [applicationUserID];
      const expectedCollection: IApplicationUser[] = [...additionalApplicationUsers, ...applicationUserCollection];
      jest.spyOn(applicationUserService, 'addApplicationUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ party });
      comp.ngOnInit();

      expect(applicationUserService.query).toHaveBeenCalled();
      expect(applicationUserService.addApplicationUserToCollectionIfMissing).toHaveBeenCalledWith(
        applicationUserCollection,
        ...additionalApplicationUsers
      );
      expect(comp.applicationUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PartyGroup query and add missing value', () => {
      const party: IParty = { id: 456 };
      const partyGroupID: IPartyGroup = { id: 76508 };
      party.partyGroupID = partyGroupID;

      const partyGroupCollection: IPartyGroup[] = [{ id: 3426 }];
      jest.spyOn(partyGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: partyGroupCollection })));
      const additionalPartyGroups = [partyGroupID];
      const expectedCollection: IPartyGroup[] = [...additionalPartyGroups, ...partyGroupCollection];
      jest.spyOn(partyGroupService, 'addPartyGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ party });
      comp.ngOnInit();

      expect(partyGroupService.query).toHaveBeenCalled();
      expect(partyGroupService.addPartyGroupToCollectionIfMissing).toHaveBeenCalledWith(partyGroupCollection, ...additionalPartyGroups);
      expect(comp.partyGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PartyType query and add missing value', () => {
      const party: IParty = { id: 456 };
      const partyTypeID: IPartyType = { id: 14076 };
      party.partyTypeID = partyTypeID;

      const partyTypeCollection: IPartyType[] = [{ id: 21212 }];
      jest.spyOn(partyTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: partyTypeCollection })));
      const additionalPartyTypes = [partyTypeID];
      const expectedCollection: IPartyType[] = [...additionalPartyTypes, ...partyTypeCollection];
      jest.spyOn(partyTypeService, 'addPartyTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ party });
      comp.ngOnInit();

      expect(partyTypeService.query).toHaveBeenCalled();
      expect(partyTypeService.addPartyTypeToCollectionIfMissing).toHaveBeenCalledWith(partyTypeCollection, ...additionalPartyTypes);
      expect(comp.partyTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const party: IParty = { id: 456 };
      const partyRoleID: IPartyRole = { id: 99637 };
      party.partyRoleID = partyRoleID;
      const personID: IPerson = { id: 55753 };
      party.personID = personID;
      const applicationUserID: IApplicationUser = { id: 9803 };
      party.applicationUserID = applicationUserID;
      const partyGroupID: IPartyGroup = { id: 14886 };
      party.partyGroupID = partyGroupID;
      const partyTypeID: IPartyType = { id: 96427 };
      party.partyTypeID = partyTypeID;

      activatedRoute.data = of({ party });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(party));
      expect(comp.partyRolesSharedCollection).toContain(partyRoleID);
      expect(comp.peopleSharedCollection).toContain(personID);
      expect(comp.applicationUsersSharedCollection).toContain(applicationUserID);
      expect(comp.partyGroupsSharedCollection).toContain(partyGroupID);
      expect(comp.partyTypesSharedCollection).toContain(partyTypeID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Party>>();
      const party = { id: 123 };
      jest.spyOn(partyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ party });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: party }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(partyService.update).toHaveBeenCalledWith(party);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Party>>();
      const party = new Party();
      jest.spyOn(partyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ party });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: party }));
      saveSubject.complete();

      // THEN
      expect(partyService.create).toHaveBeenCalledWith(party);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Party>>();
      const party = { id: 123 };
      jest.spyOn(partyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ party });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(partyService.update).toHaveBeenCalledWith(party);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPartyRoleById', () => {
      it('Should return tracked PartyRole primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPartyRoleById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPersonById', () => {
      it('Should return tracked Person primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPersonById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackApplicationUserById', () => {
      it('Should return tracked ApplicationUser primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackApplicationUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPartyGroupById', () => {
      it('Should return tracked PartyGroup primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPartyGroupById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPartyTypeById', () => {
      it('Should return tracked PartyType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPartyTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
