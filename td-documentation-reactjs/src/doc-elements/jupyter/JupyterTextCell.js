import React from 'react'

const JupyterTextCell = ({text, elementsLibrary}) => {
    return (
        <div className="jupyter-text content-block">
            <elementsLibrary.CliOutput lines={text.split('\n')}/>
        </div>
    )
}

export default JupyterTextCell
