import React, { useState } from "react";
import { DragDropContext, Droppable, Draggable } from "react-beautiful-dnd";


// fake data generator
const getItems = (count: number, offset = 0) =>
    Array.from({ length: count }, (v, k) => k).map(k => ({
      id: `item-${k + offset}-${new Date().getTime()}`,
      content: `item ${k + offset}`
    }));

const reorder = (list: Array<any>, startIndex:number , endIndex:number) => {
  const result = Array.from(list);
  const [removed] = result.splice(startIndex, 1);
  result.splice(endIndex, 0, removed);

  return result;
};

/**
 * Moves an item from one list to another list.
 */
const move = (source :any, destination: any, droppableSource:any, droppableDestination:any) => {
  const sourceClone = Array.from(source);
  const destClone = Array.from(destination);
  const [removed] = sourceClone.splice(droppableSource.index, 1);

  destClone.splice(droppableDestination.index, 0, removed);

  const result : {} = {};
  // @ts-ignore
    result[droppableSource.droppableId] = sourceClone;
  // @ts-ignore
    result[droppableDestination.droppableId] = destClone;

  return result;
};
const grid = 8;

const getItemStyle = (isDragging:boolean , draggableStyle: any) => ({
  // some basic styles to make the items look a bit nicer
  userSelect: "none",
  padding: grid * 2,
  margin: `0 0 ${grid}px 0`,

  // change background colour if dragging
  background: isDragging ? "lightgreen" : "grey",

  // styles we need to apply on draggables
  ...draggableStyle
});
const getListStyle = (isDraggingOver :boolean) => ({
  background: isDraggingOver ? "lightblue" : "lightgrey",
  padding: grid,
  width: 250
});

function QuoteApp() {
  const [state, setState] = useState([getItems(10), getItems(5, 10)]);

  function onDragEnd(result:any) {
    const { source, destination } = result;

    // dropped outside the list
    if (!destination) {
      return;
    }
    const sInd = +source.droppableId;
    const dInd = +destination.droppableId;

    if (sInd === dInd) {
      const items = reorder(state[sInd], source.index, destination.index);
      const newState = [...state];
      newState[sInd] = items;
      setState(newState);
    } else {
      const result = move(state[sInd], state[dInd], source, destination);
      const newState = [...state];
      // @ts-ignore
        newState[sInd] = result[sInd];
      // @ts-ignore
        newState[dInd] = result[dInd];

      setState(newState.filter(group => group.length));
    }
  }

  return (
      <div>
        <button
            type="button"
            onClick={() => {
              setState([...state, []]);
            }}
        >
          Add new group
        </button>
        <button
            type="button"
            onClick={() => {
              setState([...state, getItems(1)]);
            }}
        >
          Add new item
        </button>
        <div style={{ display: "flex" }}>
          <DragDropContext onDragEnd={onDragEnd}>
            {state.map((el, ind) => (
                <Droppable key={ind} droppableId={`${ind}`}>
                  {(provided, snapshot) => (
                      <div
                          ref={provided.innerRef}
                          style={getListStyle(snapshot.isDraggingOver)}
                          {...provided.droppableProps}
                      >
                        {el.map((item, index) => (
                            <Draggable
                                key={item.id}
                                draggableId={item.id}
                                index={index}
                            >
                              {(provided, snapshot) => (
                                  <div
                                      ref={provided.innerRef}
                                      {...provided.draggableProps}
                                      {...provided.dragHandleProps}
                                      style={getItemStyle(
                                          snapshot.isDragging,
                                          provided.draggableProps.style
                                      )}
                                  >
                                    <div
                                        style={{
                                          display: "flex",
                                          justifyContent: "space-around"
                                        }}
                                    >
                                      {item.content}
                                      <button
                                          type="button"
                                          onClick={() => {
                                            const newState = [...state];
                                            newState[ind].splice(index, 1);
                                            setState(
                                                newState.filter(group => group.length)
                                            );
                                          }}
                                      >
                                        delete
                                      </button>
                                    </div>
                                  </div>
                              )}
                            </Draggable>
                        ))}
                        {provided.placeholder}
                      </div>
                  )}
                </Droppable>
            ))}
          </DragDropContext>
        </div>
      </div>
  );
}

export default QuoteApp;
