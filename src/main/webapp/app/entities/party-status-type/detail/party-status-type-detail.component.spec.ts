import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PartyStatusTypeDetailComponent } from './party-status-type-detail.component';

describe('PartyStatusType Management Detail Component', () => {
  let comp: PartyStatusTypeDetailComponent;
  let fixture: ComponentFixture<PartyStatusTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PartyStatusTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ partyStatusType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PartyStatusTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PartyStatusTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load partyStatusType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.partyStatusType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
