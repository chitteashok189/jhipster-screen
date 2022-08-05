import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PartyRoleDetailComponent } from './party-role-detail.component';

describe('PartyRole Management Detail Component', () => {
  let comp: PartyRoleDetailComponent;
  let fixture: ComponentFixture<PartyRoleDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PartyRoleDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ partyRole: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PartyRoleDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PartyRoleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load partyRole on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.partyRole).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
