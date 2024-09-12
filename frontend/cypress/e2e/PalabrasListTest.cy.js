describe('Palabras List', () => {
    beforeEach(() => {
      cy.visit('/palabras');
    });
  
    it('should display the list of palabras', () => {
      cy.get('.cards-container').should('be.visible');
    });
  
    it('should navigate to the form to create a new palabra', () => {
      cy.get('.btn-create').click();
      cy.url().should('include', '/palabras-form');
    });

    it('should display the list of palabras', () => {
      cy.get('.cards-container').should('be.visible');
    });
  
    it('should navigate to the form to create a new palabra', () => {
      cy.get('.btn-create').click();
      cy.url().should('include', '/palabras-form');
    });
  
  
  
    it('should search for a palabra', () => {
      cy.get('.search-input').type('Test Palabra');
      cy.get('.cards-container .card').each(($card) => {
        cy.wrap($card).find('.card-header').should('contain', 'Test Palabra');
      });
    });
  
   
    it('should edit a palabra', () => {
      cy.get('.btn-edit').first().click();
      cy.url().should('include', '/palabras-form');
    });
  
   
    it('should cancel delete a palabra', () => {
      cy.get('.btn-delete').first().click();
      cy.get('.modal').should('be.visible');
      cy.get('.btn-cancel').click();
      cy.get('.modal').should('not.exist');
    });
  });