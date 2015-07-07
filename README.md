# contaminated-cell-automaton
## A specialised cell automaton for modelling adhesive cell patterning systems
### Introduction
#### Adhesive cell patterning
The reaction/diffusion cell patterning systems proposed by Turing and Meinhardt, among others, require an initial condition, such as a so-called morphogen gradient, to induce patterning (Maini *et al.*, 2012). Adhesive cell patterning does not require this initial condition and sorting occurs when cells move to decrease the overall energy of the system. Each cell type is associated with a specific binding energy to each cell type in the system, including itself. This patterning system has been engineered *in vitro* by Cachat *et al.* in 2015 at the Centre for Integrative Physiology at the University of Edinburgh.

#### Induced pluripotent stem cells and engineered organ development
Induced pluripotent stem cells were first developed by Takahashi and Yamanaka in 2006. They used adult somatic cells (such as fibroblasts, or skin cells) and induced them to revert to embryonic stem cell-like pluripotency using four transcription factors. These pluripotent stem cells can then be induced to differentiate into other cell types. The technique has taken off and many labs worldwide are using iPSCs to generate systems using stem cells created from human adults with a condition or gene of interest. 

This technique shows promise for the development of organs that can be engineered from the patient's own cells, thus eliminating the issues associated with transplantation and immune rejection. 

#### The problem with iPSC renal differentiation and engineered kidney development
While most stem cells properly differentiate into renal cells, some cells differentiate incorrectly into other, irrelevant cell types. We call these "contaminating" cells. These contaminating cells are thought to disrupt the proper patterning of renal cells, resulting in the non-formation or malformation of the mature kidney. While these contaminating cells could disrupt patterning by secreting irrelevant morphogens into the environment, we think that they represent a physical barrier to the correct cells as they self-sort. In other words, contaminating cells simply get in the way. In order to build human kidneys from iPSCs, it is imperative that renal cells can properly sort, and any effect contaminating cells may have on this patterning must be eradicated.

#### Computational modelling of the contaminated cell patterning system
This program is an extension of a simple cell automaton written in Processing by Jamie Davies, which contained two cell types (red and green) each with binding energies to itself and to the opposite cell type. 
